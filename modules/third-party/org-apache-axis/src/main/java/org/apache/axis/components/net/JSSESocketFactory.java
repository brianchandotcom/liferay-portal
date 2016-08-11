/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.axis.components.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.axis.utils.Messages;
import org.apache.axis.utils.StringUtils;
import org.apache.axis.utils.XMLUtils;


/**
 * SSL socket factory. It _requires_ a valid RSA key and
 * JSSE. (borrowed code from tomcat)
 * 
 * THIS CODE STILL HAS DEPENDENCIES ON sun.* and com.sun.*
 *
 * @author Davanum Srinivas (dims@yahoo.com)
 */
public class JSSESocketFactory extends DefaultSocketFactory implements SecureSocketFactory {

    // This is a a sorted list, if you insert new elements do it orderdered.
    private final static String[] BAD_COUNTRY_2LDS =
        {"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info",
            "lg", "ne", "net", "or", "org"};
    /** Field sslFactory           */
    protected SSLSocketFactory sslFactory = null;

    /**
     * Constructor JSSESocketFactory
     *
     * @param attributes
     */
    public JSSESocketFactory(Hashtable attributes) {
        super(attributes);
    }

    /**
     * Initialize the SSLSocketFactory
     * @throws IOException
     */ 
    protected void initFactory() throws IOException {
        sslFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
    }
    
    /**
     * creates a secure socket
     *
     * @param host
     * @param port
     * @param otherHeaders
     * @param useFullURL
     *
     * @return Socket
     * @throws Exception
     */
    public Socket create(
            String host, int port, StringBuffer otherHeaders, BooleanHolder useFullURL)
            throws Exception {
        if (sslFactory == null) {
            initFactory();
        }
        if (port == -1) {
            port = 443;
        }

        TransportClientProperties tcp = TransportClientPropertiesFactory.create("https");

        boolean hostInNonProxyList = isHostInNonProxyList(host, tcp.getNonProxyHosts());

        Socket sslSocket = null;
        if (tcp.getProxyHost().length() == 0 || hostInNonProxyList) {
            // direct SSL connection
            sslSocket = sslFactory.createSocket(host, port);
        } else {

            // Default proxy port is 80, even for https
            int tunnelPort = (tcp.getProxyPort().length() != 0)
                             ? Integer.parseInt(tcp.getProxyPort())
                             : 80;
            if (tunnelPort < 0)
                tunnelPort = 80;

            // Create the regular socket connection to the proxy
            Socket tunnel = new Socket(tcp.getProxyHost(), tunnelPort);

            // The tunnel handshake method (condensed and made reflexive)
            OutputStream tunnelOutputStream = tunnel.getOutputStream();
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(tunnelOutputStream)));

            // More secure version... engage later?
            // PasswordAuthentication pa =
            // Authenticator.requestPasswordAuthentication(
            // InetAddress.getByName(tunnelHost),
            // tunnelPort, "SOCK", "Proxy","HTTP");
            // if(pa == null){
            // printDebug("No Authenticator set.");
            // }else{
            // printDebug("Using Authenticator.");
            // tunnelUser = pa.getUserName();
            // tunnelPassword = new String(pa.getPassword());
            // }
            out.print("CONNECT " + host + ":" + port + " HTTP/1.0\r\n"
                    + "User-Agent: AxisClient");
            if (tcp.getProxyUser().length() != 0 &&
                tcp.getProxyPassword().length() != 0) {

                // add basic authentication header for the proxy
                String encodedPassword = XMLUtils.base64encode((tcp.getProxyUser()
                        + ":"
                        + tcp.getProxyPassword()).getBytes());

                out.print("\nProxy-Authorization: Basic " + encodedPassword);
            }
            out.print("\nContent-Length: 0");
            out.print("\nPragma: no-cache");
            out.print("\r\n\r\n");
            out.flush();
            InputStream tunnelInputStream = tunnel.getInputStream();

            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("isNull00", "tunnelInputStream",
                        "" + (tunnelInputStream
                        == null)));
            }
            String replyStr = "";

            // Make sure to read all the response from the proxy to prevent SSL negotiation failure
            // Response message terminated by two sequential newlines
            int newlinesSeen = 0;
            boolean headerDone = false;    /* Done on first newline */

            while (newlinesSeen < 2) {
                int i = tunnelInputStream.read();

                if (i < 0) {
                    throw new IOException("Unexpected EOF from proxy");
                }
                if (i == '\n') {
                    headerDone = true;
                    ++newlinesSeen;
                } else if (i != '\r') {
                    newlinesSeen = 0;
                    if (!headerDone) {
                        replyStr += String.valueOf((char) i);
                    }
                }
            }
            if (StringUtils.startsWithIgnoreWhitespaces("HTTP/1.0 200", replyStr) &&
                    StringUtils.startsWithIgnoreWhitespaces("HTTP/1.1 200", replyStr)) {
                throw new IOException(Messages.getMessage("cantTunnel00",
                        new String[]{
                            tcp.getProxyHost(),
                            "" + tunnelPort,
                            replyStr}));
            }

            // End of condensed reflective tunnel handshake method
            sslSocket = sslFactory.createSocket(tunnel, host, port, true);
            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("setupTunnel00",
                          tcp.getProxyHost(),
                        "" + tunnelPort));
            }
        }

        ((SSLSocket) sslSocket).startHandshake();
        if (log.isDebugEnabled()) {
            log.debug(Messages.getMessage("createdSSL00"));
        }
        verifyHostName(host, (SSLSocket) sslSocket);
        return sslSocket;
    }
    /**
     * Verifies that the given hostname in certicifate is the hostname we are trying to connect to.
     * This resolves CVE-2012-5784 and CVE-2014-3596
     * @param host
     * @param ssl
     * @throws IOException
     */
    
	private static void verifyHostName(String host, SSLSocket ssl)
			throws IOException {
		if (host == null) {
			throw new IllegalArgumentException("host to verify was null");
		}

		SSLSession session = ssl.getSession();
		if (session == null) {
            // In our experience this only happens under IBM 1.4.x when
            // spurious (unrelated) certificates show up in the server's chain.
            // Hopefully this will unearth the real problem:
			InputStream in = ssl.getInputStream();
			in.available();
            /*
                 If you're looking at the 2 lines of code above because you're
                 running into a problem, you probably have two options:

                    #1.  Clean up the certificate chain that your server
                         is presenting (e.g. edit "/etc/apache2/server.crt" or
                         wherever it is your server's certificate chain is
                         defined).

                                             OR

                    #2.   Upgrade to an IBM 1.5.x or greater JVM, or switch to a
                          non-IBM JVM.
              */

            // If ssl.getInputStream().available() didn't cause an exception,
            // maybe at least now the session is available?
			session = ssl.getSession();
			if (session == null) {
                // If it's still null, probably a startHandshake() will
                // unearth the real problem.
				ssl.startHandshake();

                // Okay, if we still haven't managed to cause an exception,
                // might as well go for the NPE.  Or maybe we're okay now?
				session = ssl.getSession();
			}
		}

		Certificate[] certs = session.getPeerCertificates();
		verifyHostName(host.trim().toLowerCase(Locale.US),  (X509Certificate) certs[0]);
	}
	/**
	 * Extract the names from the certificate and tests host matches one of them
	 * @param host
	 * @param cert
	 * @throws SSLException
	 */

	private static void verifyHostName(final String host, X509Certificate cert)
			throws SSLException {
        // I'm okay with being case-insensitive when comparing the host we used
        // to establish the socket to the hostname in the certificate.
        // Don't trim the CN, though.
        
		String[] cns = getCNs(cert);
		String[] subjectAlts = getDNSSubjectAlts(cert);
		verifyHostName(host, cns, subjectAlts);

	}

	/**
	 * Extract all alternative names from a certificate.
	 * @param cert
	 * @return
	 */
	private static String[] getDNSSubjectAlts(X509Certificate cert) {
		LinkedList subjectAltList = new LinkedList();
		Collection c = null;
		try {
			c = cert.getSubjectAlternativeNames();
		} catch (CertificateParsingException cpe) {
			// Should probably log.debug() this?
			cpe.printStackTrace();
		}
		if (c != null) {
			Iterator it = c.iterator();
			while (it.hasNext()) {
				List list = (List) it.next();
				int type = ((Integer) list.get(0)).intValue();
				// If type is 2, then we've got a dNSName
				if (type == 2) {
					String s = (String) list.get(1);
					subjectAltList.add(s);
				}
			}
		}
		if (!subjectAltList.isEmpty()) {
			String[] subjectAlts = new String[subjectAltList.size()];
			subjectAltList.toArray(subjectAlts);
			return subjectAlts;
		} else {
			return new String[0];
		}
	        
	}
	/**
	 * Verifies
	 * @param host
	 * @param cn
	 * @param subjectAlts
	 * @throws SSLException
	 */

	private static void verifyHostName(final String host, String[] cns, String[] subjectAlts)throws SSLException{
		StringBuffer cnTested = new StringBuffer();

		for (int i = 0; i < subjectAlts.length; i++){
			String name = subjectAlts[i];
			if (name != null) {
				name = name.toLowerCase(Locale.US);
				if (verifyHostName(host, name)){
					return;
				}
				cnTested.append("/").append(name);
			}				
		}
        for (int i = 0; i < cns.length; i++) {
            String cn = cns[i];
            if (cn != null) {
                cn = cn.toLowerCase(Locale.US);
                if (verifyHostName(host, cn)) {
                    return;
                }
                cnTested.append("/").append(cn);
            }
        }
		throw new SSLException("hostname in certificate didn't match: <"
					+ host + "> != <" + cnTested + ">");
	}		
	
	private static boolean verifyHostName(final String host, final String cn){
		if (doWildCard(cn) && !isIPAddress(host)) {
			return matchesWildCard(cn, host);
		} 
		return host.equalsIgnoreCase(cn);
	}
    private static boolean doWildCard(String cn) {
		// Contains a wildcard
		// wildcard in the first block
    	// not an ipaddress (ip addres must explicitily be equal)
    	// not using 2nd level common tld : ex: not for *.co.uk
    	String parts[] = cn.split("\\.");
    	return parts.length >= 3 &&
    			parts[0].endsWith("*") &&
    			acceptableCountryWildcard(cn) &&
    			!isIPAddress(cn);
    }

	private static final Pattern IPV4_PATTERN =
			Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

	private static final Pattern IPV6_STD_PATTERN = 
			Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

	private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = 
			Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");


	private static boolean isIPAddress(final String hostname) {
		return hostname != null
				&& (
						IPV4_PATTERN.matcher(hostname).matches()
						|| IPV6_STD_PATTERN.matcher(hostname).matches() 
						|| IPV6_HEX_COMPRESSED_PATTERN.matcher(hostname).matches()
		);

	}

	private static boolean acceptableCountryWildcard(final String cn) {
		// The CN better have at least two dots if it wants wildcard action,
		// but can't be [*.co.uk] or [*.co.jp] or [*.org.uk], etc...
		// The [*.co.uk] problem is an interesting one. Should we just
		// hope that CA's would never foolishly allow such a
		// certificate to happen?
    	
		String[] parts = cn.split("\\.");
		// Only checks for 3 levels, with country code of 2 letters.
		if (parts.length > 3 || parts[parts.length - 1].length() != 2) {
			return true;
		}
		String countryCode = parts[parts.length - 2];
		return Arrays.binarySearch(BAD_COUNTRY_2LDS, countryCode) < 0;
	}

	private static boolean matchesWildCard(final String cn,
			final String hostName) {
		String parts[] = cn.split("\\.");
		boolean match = false;
		String firstpart = parts[0];
		if (firstpart.length() > 1) {
			// server∗
			// e.g. server
			String prefix =  firstpart.substring(0, firstpart.length() - 1);
			// skipwildcard part from cn
			String suffix = cn.substring(firstpart.length()); 
			// skip wildcard part from host
			String hostSuffix = hostName.substring(prefix.length());			
			match = hostName.startsWith(prefix) && hostSuffix.endsWith(suffix);
		} else {
			match = hostName.endsWith(cn.substring(1));
		}
		if (match) {
			// I f we ’ r e i n s t r i c t mode ,
			// [ ∗.foo.com] is not allowed to match [a.b.foo.com]
			match = countDots(hostName) == countDots(cn);
		}
		return match;
	}

	private static int countDots(final String data) {
		int dots = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '.') {
				dots += 1;
			}
		}
		return dots;
	}


	private static String[] getCNs(X509Certificate cert) {
          // Note:  toString() seems to do a better job than getName()
          //
          // For example, getName() gives me this:
          // 1.2.840.113549.1.9.1=#16166a756c6975736461766965734063756362632e636f6d
          //
          // whereas toString() gives me this:
          // EMAILADDRESS=juliusdavies@cucbc.com        
		String subjectPrincipal = cert.getSubjectX500Principal().toString();
		
		return getCNs(subjectPrincipal);

	}
	private static String[] getCNs(String subjectPrincipal) {
        if (subjectPrincipal == null) {
            return null;
        }
        final List cns = new ArrayList();
        try {
            final LdapName subjectDN = new LdapName(subjectPrincipal);
            final List rdns = subjectDN.getRdns();
            for (int i = rdns.size() - 1; i >= 0; i--) {
                final Rdn rds = (Rdn) rdns.get(i);
                final Attributes attributes = rds.toAttributes();
                final Attribute cn = attributes.get("cn");
                if (cn != null) {
                    try {
                        final Object value = cn.get();
                        if (value != null) {
                            cns.add(value.toString());
                        }
                    }
                    catch (NamingException ignore) {}
                }
            }
        }
        catch (InvalidNameException ignore) { }
        return cns.isEmpty() ? null : (String[]) cns.toArray(new String[ cns.size() ]);
	}

}
/* @generated */