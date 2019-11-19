/*******************************************************************************
 * Copyright (c) 2005, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.metatype.impl;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javax.xml.parsers.SAXParser;
import org.eclipse.equinox.metatype.EquinoxObjectClassDefinition;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.service.log.LogService;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.MetaTypeService;

/**
 * Implementation of MetaTypeProvider
 */
public class MetaTypeProviderImpl implements MetaTypeProvider {

	public static final String METADATA_NOT_FOUND = "METADATA_NOT_FOUND"; //$NON-NLS-1$
	public static final String OCD_ID_NOT_FOUND = "OCD_ID_NOT_FOUND"; //$NON-NLS-1$
	public static final String ASK_INVALID_LOCALE = "ASK_INVALID_LOCALE"; //$NON-NLS-1$

	public static final String META_FILE_EXT = ".XML"; //$NON-NLS-1$
	public static final String RESOURCE_FILE_CONN = "_"; //$NON-NLS-1$
	public static final String RESOURCE_FILE_EXT = ".properties"; //$NON-NLS-1$
	public static final char DIRECTORY_SEP = '/';

	Bundle _bundle;

	Map<String, ObjectClassDefinitionImpl> _allPidOCDs = new HashMap<>();
	Map<String, ObjectClassDefinitionImpl> _allFPidOCDs = new HashMap<>();

	String[] _locales;
	boolean _isThereMeta = false;

	// Give access to subclasses.
	protected final LogService logger;

	/**
	 * Constructor of class MetaTypeProviderImpl.
	 */
	MetaTypeProviderImpl(Bundle bundle, SAXParser parser, LogService logger) {

		this._bundle = bundle;
		this.logger = logger;

		// read all bundle's metadata files and build internal data structures
		_isThereMeta = readMetaFiles(bundle, parser);

		if (!_isThereMeta) {
			logger.log(LogService.LOG_DEBUG, NLS.bind(MetaTypeMsg.METADATA_NOT_FOUND, bundle.getSymbolicName(), bundle.getBundleId()));
		}
	}

	/**
	 * This method should do the following:
	 * <p> - Obtain a SAX parser from the XML Parser Service:
	 * <p>
	 * 
	 * <pre>	</pre>
	 * 
	 * The parser may be SAX 1 (eXML) or SAX 2 (XML4J). It should attempt to use
	 * a SAX2 parser by instantiating an XMLReader and extending DefaultHandler
	 * BUT if that fails it should fall back to instantiating a SAX1 Parser and
	 * extending HandlerBase.
	 * <p> - Pass the parser the URL for the bundle's METADATA.XML file
	 * <p> - Handle the callbacks from the parser and build the appropriate
	 * MetaType objects - ObjectClassDefinitions & AttributeDefinitions
	 * 
	 * @param bundle The bundle object for which the metadata should be read
	 * @param parserFactory The bundle object for which the metadata should be
	 *        read
	 * @return void
	 * @throws IOException If there are errors accessing the metadata.xml file
	 */
	private boolean readMetaFiles(Bundle bundle, SAXParser saxParser) {
		Enumeration<URL> entries = bundle.findEntries(MetaTypeService.METATYPE_DOCUMENTS_LOCATION, "*", false); //$NON-NLS-1$
		if (entries == null)
			return false;
		boolean result = false;
		for (URL entry : Collections.list(entries)) {
			if (entry.getPath().endsWith("/")) //$NON-NLS-1$
				continue;
			DataParser parser = new DataParser(bundle, entry, saxParser, logger);
			try {
				Collection<Designate> designates = parser.doParse();
				if (!designates.isEmpty()) {
					result = true;
				}
				for (Designate designate : designates) {
					if (designate.isFactory()) {
						_allFPidOCDs.put(designate.getFactoryPid(), designate.getObjectClassDefinition());
					} else {
						_allPidOCDs.put(designate.getPid(), designate.getObjectClassDefinition());
					}
				}
			} catch (Exception e) {
				logger.log(LogService.LOG_ERROR, NLS.bind(MetaTypeMsg.METADATA_FILE_PARSE_ERROR, new Object[] {entry, bundle.getBundleId(), bundle.getSymbolicName()}), e);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.MetaTypeProvider#getObjectClassDefinition(java.lang.String,
	 *      java.lang.String)
	 */
	public EquinoxObjectClassDefinition getObjectClassDefinition(String pid, String locale) {

		if (isInvalidLocale(locale)) {
			throw new IllegalArgumentException(NLS.bind(MetaTypeMsg.ASK_INVALID_LOCALE, pid, locale));
		}

		ObjectClassDefinitionImpl ocd;
		if (_allPidOCDs.containsKey(pid)) {
			ocd = (ObjectClassDefinitionImpl) (_allPidOCDs.get(pid)).clone();
			ocd.setResourceBundle(locale, _bundle);
			return ocd;
		} else if (_allFPidOCDs.containsKey(pid)) {
			ocd = (ObjectClassDefinitionImpl) (_allFPidOCDs.get(pid)).clone();
			ocd.setResourceBundle(locale, _bundle);
			return ocd;
		} else {
			throw new IllegalArgumentException(NLS.bind(MetaTypeMsg.OCD_PID_NOT_FOUND, pid));
		}
	}

	/**
	 * Internal Method - Check if the locale is invalid.
	 */
	public boolean isInvalidLocale(String locale) {

		// Just a simple and quick check here.
		if (locale == null || locale.length() == 0)
			return false;

		int idx_first = locale.indexOf(LocalizationElement.LOCALE_SEP);
		int idx_second = locale.lastIndexOf(LocalizationElement.LOCALE_SEP);
		if (idx_first == -1 && (locale.length() == 2 || locale.length() == 3))
			// It is format of only language.
			return false;
		if (((idx_first == 2) && (idx_second == 5 || idx_second == 2)) || ((idx_first == 3) && (idx_second == 6 || idx_second == 3)))
			// It is format of language + "_" + country [ + "_" + variation ].
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.MetaTypeProvider#getLocales()
	 */
	public synchronized String[] getLocales() {
		if (_locales != null)
			return checkForDefault(_locales);
		Vector<String> localizationFiles = new Vector<String>(7);
		// get all the localization resources for PIDS
		for (ObjectClassDefinitionImpl ocd : _allPidOCDs.values()) {
			String localization = ocd.getLocalization();
			if (localization != null && !localizationFiles.contains(localization))
				localizationFiles.add(localization);
		}
		// get all the localization resources for FPIDS
		for (ObjectClassDefinitionImpl ocd : _allFPidOCDs.values()) {
			String localization = ocd.getLocalization();
			if (localization != null && !localizationFiles.contains(localization))
				localizationFiles.add(localization);
		}
		if (localizationFiles.size() == 0)
			localizationFiles.add(getBundleLocalization(_bundle));
		Vector<String> locales = new Vector<String>(7);
		Enumeration<String> eLocalizationFiles = localizationFiles.elements();
		while (eLocalizationFiles.hasMoreElements()) {
			String localizationFile = eLocalizationFiles.nextElement();
			int iSlash = localizationFile.lastIndexOf(DIRECTORY_SEP);
			String baseDir;
			String baseFileName;
			if (iSlash < 0) {
				baseDir = ""; //$NON-NLS-1$
			} else {
				baseDir = localizationFile.substring(0, iSlash);
			}
			baseFileName = '/' + localizationFile + RESOURCE_FILE_CONN;
			Enumeration<URL> entries = _bundle.findEntries(baseDir, "*.properties", false); //$NON-NLS-1$
			if (entries == null)
				continue;
			while (entries.hasMoreElements()) {
				String resource = entries.nextElement().getPath();
				if (resource.startsWith(baseFileName) && resource.toLowerCase().endsWith(RESOURCE_FILE_EXT))
					locales.add(resource.substring(baseFileName.length(), resource.length() - RESOURCE_FILE_EXT.length()));
			}
		}
		_locales = locales.toArray(new String[locales.size()]);
		return checkForDefault(_locales);
	}

	static String getBundleLocalization(Bundle bundle) {
		// Use the Bundle-Localization manifest header value if it exists.
		String baseName = bundle.getHeaders("").get(Constants.BUNDLE_LOCALIZATION); //$NON-NLS-1$
		if (baseName == null)
			// If the manifest header does not exist, use the default.
			baseName = Constants.BUNDLE_LOCALIZATION_DEFAULT_BASENAME;
		return baseName;
	}

	/**
	 * Internal Method - checkForDefault
	 */
	private String[] checkForDefault(String[] locales) {

		if (locales == null || locales.length == 0 || (locales.length == 1 && Locale.getDefault().toString().equals(locales[0])))
			return null;
		return locales;
	}
}
/* @generated */