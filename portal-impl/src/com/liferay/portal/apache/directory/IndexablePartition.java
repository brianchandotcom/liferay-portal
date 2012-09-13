/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.apache.directory;

import org.apache.directory.server.core.api.partition.Partition;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.shared.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.shared.ldap.model.name.Dn;

/**
 * @author Manuel de la Peña
 */
public class IndexablePartition {

	public IndexablePartition(Partition partition)
		throws LdapInvalidDnException {

		_jdbmPartition = (JdbmPartition)partition;
	}

	public Partition getPartition() {
		return _jdbmPartition;
	}

	public Dn getSuffixDn() throws Exception {
		return _jdbmPartition.getSuffixDn();
	}

	private JdbmPartition _jdbmPartition = null;

}