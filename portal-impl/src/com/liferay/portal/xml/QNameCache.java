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

package com.liferay.portal.xml;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.DocumentFactory;
import org.dom4j.QName;

/**
 * @author Jorge Díaz
 */
public class QNameCache extends org.dom4j.tree.QNameCache {

	public QNameCache(DocumentFactory documentFactory) {
		super(documentFactory);

		namespaceCache = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
		noNamespaceCache = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	@Override
	protected Map<String, QName> createMap() {
		return new ConcurrentHashMap<>();
	}

}