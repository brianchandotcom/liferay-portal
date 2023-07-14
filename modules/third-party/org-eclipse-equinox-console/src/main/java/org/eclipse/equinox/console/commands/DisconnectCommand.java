/*******************************************************************************
 * Copyright (c) 2011, 2017 SAP AG and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Lazar Kirchev, SAP AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.console.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.io.PrintStream;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Job;
import org.apache.felix.service.command.Job.Status;
import org.eclipse.equinox.console.telnet.TelnetConnection;

import java.io.Closeable;
import org.osgi.framework.BundleContext;

/**
 * This class implements functionality to disconnect from telnet or ssh console.
 */
public class DisconnectCommand {
	private static final String DISCONNECT_MESSAGE = "Disconnect from console? (y/n; default=y) ";
	private static final String DISCONNECT_CONFIRMATION_Y = "y";
	
	private BundleContext context;
	
	public DisconnectCommand(BundleContext context) {
		this.context = context;
	}
	
	public void startService() {
		Dictionary<String, Object> props = new Hashtable<>();
		props.put(CommandProcessor.COMMAND_SCOPE, "equinox");
		props.put(CommandProcessor.COMMAND_FUNCTION, new String[] {"disconnect"});
		context.registerService(DisconnectCommand.class.getName(), this, props);
	}
	
	public void disconnect(CommandSession session) {
		PrintStream consoleStream = session.getConsole();
		consoleStream.print(DISCONNECT_MESSAGE);
		consoleStream.flush();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String reply = null;
		try {
			reply = reader.readLine();
		} catch (IOException e) {
			consoleStream.println("Error while reading confirmation");
		}
		
		if (reply != null) {
			if (reply.toLowerCase().startsWith(DISCONNECT_CONFIRMATION_Y) || reply.length() == 0) {
				Closeable closable = (Closeable)session.get(TelnetConnection.CLOSEABLE);
				if (closable != null) {
					List<Job> jobs = session.jobs();

					if (jobs.size() == 1) {
						Job job = jobs.get(0);

						if (job.status() == Status.Foreground) {
							job.background();
						}
					}

					try {
						closable.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				
				session.close();
			}
		}
	}
}
/* @generated */
