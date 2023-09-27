/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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