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

package com.liferay.fragment.collection.contributor.inputs;

import com.liferay.fragment.contributor.BaseFragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.List;
import java.util.Objects;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	property = "fragment.collection.key=INPUTS",
	service = FragmentCollectionContributor.class
)
public class InputsFragmentCollectionContributor
	extends BaseFragmentCollectionContributor {

	@Override
	public String getFragmentCollectionKey() {
		return "INPUTS";
	}

	@Override
	public List<FragmentEntry> getFragmentEntries() {
		return _filterList(super.getFragmentEntries());
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(int type) {
		return _filterList(super.getFragmentEntries(type));
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private List<FragmentEntry> _filterList(
		List<FragmentEntry> fragmentEntries) {

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-161631"))) {
			return fragmentEntries;
		}

		return ListUtil.filter(
			fragmentEntries,
			fragmentEntry ->
				!Objects.equals(
					fragmentEntry.getFragmentEntryKey(),
					"INPUTS-multi-select-list") &&
				!Objects.equals(
					fragmentEntry.getFragmentEntryKey(),
					"INPUTS-rich-text-input") &&
				!Objects.equals(
					fragmentEntry.getFragmentEntryKey(), "INPUTS-textarea"));
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.collection.contributor.inputs)"
	)
	private ServletContext _servletContext;

}