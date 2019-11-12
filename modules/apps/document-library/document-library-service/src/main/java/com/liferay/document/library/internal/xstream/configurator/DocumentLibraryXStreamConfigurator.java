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

package com.liferay.document.library.internal.xstream.configurator;

import com.liferay.document.library.internal.lar.xstream.FileEntryConverter;
import com.liferay.document.library.internal.lar.xstream.FileVersionConverter;
import com.liferay.document.library.internal.lar.xstream.FolderConverter;
import com.liferay.exportimport.kernel.xstream.XStreamAlias;
import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamType;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.impl.RepositoryEntryImpl;
import com.liferay.portal.model.impl.RepositoryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.xstream.configurator.XStreamConfigurator;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = XStreamConfigurator.class)
public class DocumentLibraryXStreamConfigurator implements XStreamConfigurator {

	@Override
	public List<XStreamType> getAllowedXStreamTypes() {
		return null;
	}

	@Override
	public List<XStreamAlias> getXStreamAliases() {
		return ListUtil.fromArray(_xStreamAliases);
	}

	@Override
	public List<XStreamConverter> getXStreamConverters() {
		return ListUtil.fromArray(_xStreamConverters);
	}

	@Activate
	protected void activate() {
		_xStreamAliases = new XStreamAlias[] {
			new XStreamAlias(DLFileEntryImpl.class, "DLFileEntry"),
			new XStreamAlias(DLFileEntryTypeImpl.class, "DLFileEntryType"),
			new XStreamAlias(DLFileShortcutImpl.class, "DLFileShortcut"),
			new XStreamAlias(DLFolderImpl.class, "DLFolder"),
			new XStreamAlias(RepositoryImpl.class, "Repository"),
			new XStreamAlias(RepositoryEntryImpl.class, "RepositoryEntry")
		};

		_xStreamConverters = new XStreamConverter[] {
			new FileEntryConverter(), new FileVersionConverter(),
			new FolderConverter()
		};
	}

	private XStreamAlias[] _xStreamAliases;
	private XStreamConverter[] _xStreamConverters;

}