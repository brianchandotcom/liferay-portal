/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.guard.connector;

import com.liferay.data.guard.connector.command.DataGuardCommand;
import com.liferay.portal.kernel.test.rule.DataGuardTestRuleUtil;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.Logger;

/**
 * @author Matthew Tambara
 */
public class DataGuardConnectorThread extends Thread {

	public DataGuardConnectorThread(
			BundleContext bundleContext, InetAddress inetAddress, int port,
			String passcode, Logger logger)
		throws IOException {

		_bundleContext = bundleContext;
		_passcode = passcode;
		_logger = logger;

		setName("Data Guard Connector Thread");
		setDaemon(true);

		_serverSocket = new ServerSocket(port, 50, inetAddress);
	}

	public void close() throws IOException {
		interrupt();

		_serverSocket.close();
	}

	@Override
	public void run() {
		while (true) {
			try (Socket socket = _serverSocket.accept();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					socket.getOutputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(
					socket.getInputStream())) {

				String passcode = objectInputStream.readUTF();

				if ((_passcode != null) && !_passcode.equals(passcode)) {
					_logger.warn(
						"Passcode mismatch, dropped connection from {}",
						socket.getRemoteSocketAddress());

					continue;
				}

				while (true) {
					DataGuardCommand dataGuardCommand =
						(DataGuardCommand)objectInputStream.readObject();

					try {
						DataGuardTestRuleUtil.DataBag dataBag =
							dataGuardCommand.execute(_dataBagMap);

						long id = _atomicLong.incrementAndGet();

						_dataBagMap.put(id, dataBag);

						objectOutputStream.writeObject(new DataGuardResult(id));
					}
					catch (Throwable throwable) {
						_logger.error(throwable.getMessage(), throwable);

						objectOutputStream.writeObject(
							new DataGuardResult(throwable));
					}

					objectOutputStream.flush();
				}
			}
			catch (EOFException eofException) {
			}
			catch (SocketException socketException) {
				break;
			}
			catch (Exception exception) {
				_logger.error(
					"Dropped connection due to unrecoverable failure",
					exception);
			}
		}
	}

	private final AtomicLong _atomicLong = new AtomicLong();
	private final BundleContext _bundleContext;
	private final Map<Long, DataGuardTestRuleUtil.DataBag> _dataBagMap =
		new HashMap<>();
	private final Logger _logger;
	private final String _passcode;
	private final ServerSocket _serverSocket;

}