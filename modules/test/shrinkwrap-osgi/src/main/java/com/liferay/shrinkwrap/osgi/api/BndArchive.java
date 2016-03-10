/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.shrinkwrap.osgi.api;

import aQute.bnd.osgi.Jar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.jboss.shrinkwrap.api.Assignable;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;

/**
 * @author Carlos Sierra Andrés
 */
public class BndArchive implements Assignable {

	public BndArchive(Jar jar) {
		_jar = jar;
	}

	@Override
	public <TYPE extends Assignable> TYPE as(Class<TYPE> typeClass) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			_jar.write(baos);

			ZipImporter zipImporter = ShrinkWrap.create(ZipImporter.class);

			zipImporter.importFrom(
				new ByteArrayInputStream(baos.toByteArray()));

			return zipImporter.as(typeClass);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Jar getJar() {
		return _jar;
	}

	private final Jar _jar;

}