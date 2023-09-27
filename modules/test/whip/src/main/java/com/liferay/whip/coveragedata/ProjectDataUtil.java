/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.whip.coveragedata;

import com.liferay.whip.agent.InstrumentationAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author Shuyang Zhou
 */
public class ProjectDataUtil {

	public static ProjectData captureProjectData(
		boolean saveSessionData, boolean useDataFile) {

		String className = ProjectDataUtil.class.getName();

		synchronized (className.intern()) {
			try (RandomAccessFile randomAccessFile = new RandomAccessFile(
					InstrumentationAgent.getLockFile(), "rw")) {

				FileChannel fileChannel = randomAccessFile.getChannel();

				FileLock fileLock = fileChannel.lock();

				try {
					File dataFile = InstrumentationAgent.getDataFile();

					if (dataFile.exists()) {
						_projectData.merge(_readProjectData(dataFile));

						dataFile.delete();
					}

					if (saveSessionData) {
						if (useDataFile) {
							_writeProjectData(_projectData, dataFile);
						}
						else {
							_pipingBackProjectData(_projectData);
						}
					}

					return _projectData;
				}
				finally {
					fileLock.release();
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	public static ProjectData getProjectData() {
		return _projectData;
	}

	private static void _pipingBackProjectData(ProjectData projectData) {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream) {

				@Override
				protected void writeStreamHeader() {
				}

			}) {

			objectOutputStream.reset();
			objectOutputStream.writeUnshared(new ProjectDataPiper(projectData));
			objectOutputStream.flush();

			synchronized (System.out) {
				System.out.flush();

				OutputStream outputStream = new FileOutputStream(
					FileDescriptor.out);

				outputStream.write(byteArrayOutputStream.toByteArray());
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static ProjectData _readProjectData(File dataFile) {
		try (FileInputStream fileInputStream = new FileInputStream(dataFile);
			ObjectInputStream objectInputStream = new ObjectInputStream(
				fileInputStream)) {

			return (ProjectData)objectInputStream.readObject();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static void _writeProjectData(
		ProjectData projectData, File dataFile) {

		try (FileOutputStream fileOutputStream = new FileOutputStream(dataFile);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				fileOutputStream)) {

			objectOutputStream.writeObject(projectData);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static final ProjectData _projectData = new ProjectData();

	private static class ProjectDataPiper implements Serializable {

		private ProjectDataPiper(ProjectData projectData) {
			_projectData = projectData;
		}

		private void readObject(ObjectInputStream objectInputStream)
			throws ClassNotFoundException, IOException {

			objectInputStream.defaultReadObject();

			ProjectData masterProjectData = ProjectDataUtil._projectData;

			masterProjectData.merge(_projectData);
		}

		private static final long serialVersionUID = 1L;

		private final ProjectData _projectData;

	}

}