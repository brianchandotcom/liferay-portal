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

package com.liferay.document.library.configuration;

import aQute.bnd.annotation.ProviderType;
import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sergio González
 */
@ExtendedObjectClassDefinition(category = "collaboration")
@Meta.OCD(
	id = "com.liferay.document.library.configuration.DLConfiguration",
	localization = "content/Language", name = "dl-configuration-name"
)
@ProviderType
public interface DLConfiguration {

	@Meta.AD(deflt = "true", required = false)
	public boolean addDefaultStructures();

	@Meta.AD(
		deflt = "text/asp|text/css|text/ecmascript|text/html|text/javascript|text/x-c|text/x-fortran|text/x-java-source|text/x-pascal|text/x-script.perl|text/x-script.perl-module|text/xml",
		required = false
	)
	public String[] codeFileMimeTypes();

	@Meta.AD(
		deflt = "application/x-7z-compressed|application/x-ace-compressed|application/x-compressed|application/x-rar-compressed|application/x-zip-compressed|application/zip",
		required = false
	)
	public String[] compressedFileMimeTypes();

	/**
	 * Set the location of the XML file containing the configuration of the
	 * default display templates for the Document Library portlet.
	 */
	@Meta.AD(
		deflt = "com/liferay/document/library/web/template/dependencies/portlet-display-templates.xml",
		required = false
	)
	public String displayTemplatesConfig();

	@Meta.AD(deflt = "audio|image|video", required = false)
	public String[] multimediaFileMimeTypes();

	@Meta.AD(
		deflt = "application/mspowerpoint|application/powerpoint|application/vnd.apple.keynote|application/vnd.ms-powerpoint|application/vnd.oasis.opendocument.presentation|application/vnd.openxmlformats-officedocument.presentationml.presentation|application/x-mspowerpoint",
		required = false
	)
	public String[] presentationFileMimeTypes();

	@Meta.AD(
		deflt = "application/excel|application/vnd.ms-excel|application/vnd.oasis.opendocument.spreadsheet|application/vnd.openxmlformats-officedocument.spreadsheetml.sheet|application/vnd.sun.xml.calc|application/x-excel|application/x-msexcel",
		required = false
	)
	public String[] spreadSheetFileMimeTypes();

	/**
	 * Set the interval in hours on how often
	 * TemporaryFileEntriesMessageListener will run to check for expired
	 * temporary file entries.
	 */
	@Meta.AD(deflt = "1", required = false)
	public int temporaryFileEntriesCheckInterval();

	@Meta.AD(
		deflt = "application/msword|application/vnd.oasis.opendocument.text|application/vnd.openxmlformats-officedocument.wordprocessingml.document|text/plain",
		required = false
	)
	public String[] textFileMimeTypes();

	@Meta.AD(deflt = "application/pdf", required = false)
	public String[] vectorialFileMimeTypes();

}