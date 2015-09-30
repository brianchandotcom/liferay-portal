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

package com.liferay.document.library.web.exportimport.xstream.configurator;

import com.liferay.document.library.lar.xstream.FileEntryConverter;
import com.liferay.document.library.lar.xstream.FileVersionConverter;
import com.liferay.document.library.lar.xstream.FolderConverter;
import com.liferay.exportimport.xstream.configurator.XStreamConfigurator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.impl.RepositoryEntryImpl;
import com.liferay.portal.model.impl.RepositoryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.exportimport.xstream.XStreamAlias;
import com.liferay.portlet.exportimport.xstream.XStreamConverter;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = XStreamConfigurator.class)
public class DLPortletXStreamConfigurator implements XStreamConfigurator {

	@Override
	public List<XStreamAlias> getAliases() {
		return ListUtil.toList(
			new XStreamAlias[] {
				new XStreamAlias(DLFileEntryImpl.class, "DLFileEntry"),
				new XStreamAlias(DLFileEntryTypeImpl.class, "DLFileEntryType"),
				new XStreamAlias(DLFileShortcutImpl.class, "DLFileShortcut"),
				new XStreamAlias(DLFolderImpl.class, "DLFolder"),
				new XStreamAlias(RepositoryImpl.class, "Repository"),
				new XStreamAlias(RepositoryEntryImpl.class, "RepositoryEntry")
			});
	}

	@Override
	public List<XStreamConverter> getConverters() {
		return ListUtil.toList(
			new XStreamConverter[] {
				new FileEntryConverter(), new FileVersionConverter(),
				new FolderConverter()
			});
	}

}