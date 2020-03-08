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

package com.liferay.users.admin.test.util.search;

/**
 * @author Samuel Trong Tran
 */
public class OrganizationBlueprint {

	public OrganizationBlueprint() {
	}

	public OrganizationBlueprint(OrganizationBlueprint organizationBlueprint) {
		if (organizationBlueprint.assetTagNames != null) {
			throw new RuntimeException(
				"Detected usage of deprecated field: assetTagNames");
		}

		_assetTagNames = organizationBlueprint._assetTagNames;
		_name = organizationBlueprint._name;
		_parentOrganizationId = organizationBlueprint._parentOrganizationId;
		_site = organizationBlueprint._site;
		_userId = organizationBlueprint._userId;
	}

	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

	public String getName() {
		return _name;
	}

	public int getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isSite() {
		return _site;
	}

	public static class OrganizationBlueprintBuilder {

		public OrganizationBlueprintBuilder assetTagNames(
			String[] assetTagNames) {

			_organizationBlueprint._assetTagNames = assetTagNames;

			return this;
		}

		public OrganizationBlueprint build() {
			return new OrganizationBlueprint(_organizationBlueprint);
		}

		public OrganizationBlueprintBuilder name(String name) {
			_organizationBlueprint._name = name;

			return this;
		}

		public OrganizationBlueprintBuilder parentOrganizationId(
			int parentOrganizationId) {

			_organizationBlueprint._parentOrganizationId = parentOrganizationId;

			return this;
		}

		public OrganizationBlueprintBuilder site(boolean site) {
			_organizationBlueprint._site = site;

			return this;
		}

		public OrganizationBlueprintBuilder userId(long userId) {
			_organizationBlueprint._userId = userId;

			return this;
		}

		private final OrganizationBlueprint _organizationBlueprint =
			new OrganizationBlueprint();

	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	protected String[] assetTagNames;

	private String[] _assetTagNames;
	private String _name;
	private int _parentOrganizationId;
	private boolean _site;
	private long _userId;

}