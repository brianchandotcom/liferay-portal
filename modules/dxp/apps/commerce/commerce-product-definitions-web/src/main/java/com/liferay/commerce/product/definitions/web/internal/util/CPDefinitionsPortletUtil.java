/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.definitions.web.internal.util;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.search.CPAttachmentFileEntryIndexer;
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.commerce.product.search.CPInstanceIndexer;
import com.liferay.commerce.product.util.comparator.CPAttachmentFileEntryDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPAttachmentFileEntryModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPAttachmentFileEntryPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionLinkPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionModifiedDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionNameComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionRelCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionRelNameComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionRelPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionValueRelNameComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionValueRelPriorityComparator;
import com.liferay.commerce.product.util.comparator.CPDefinitionSpecificationOptionValuePriorityComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceSkuComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionsPortletUtil {

	public static OrderByComparator<CPAttachmentFileEntry>
		getCPAttachmentFileEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPAttachmentFileEntry> orderByComparator = null;

		if (orderByCol.equals("display-date")) {
			orderByComparator = new CPAttachmentFileEntryDisplayDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new CPAttachmentFileEntryModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CPAttachmentFileEntryPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPAttachmentFileEntrySort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create(
				CPAttachmentFileEntryIndexer.FIELD_DISPLAY_DATE +
					"_Number_sortable",
				reverse);
		}
		else if (orderByCol.equals("modified-date")) {
			sort = SortFactoryUtil.create(
				Field.MODIFIED_DATE + "_sortable", reverse);
		}
		else if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(
				Field.PRIORITY, Sort.INT_TYPE, reverse);
		}

		return sort;
	}

	public static OrderByComparator<CPDefinitionLink>
		getCPDefinitionLinkOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinitionLink> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CPDefinitionLinkPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CPDefinitionOptionRel>
		getCPDefinitionOptionRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinitionOptionRel> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPDefinitionOptionRelCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CPDefinitionOptionRelPriorityComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CPDefinitionOptionRelNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPDefinitionOptionRelSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(
				Field.CREATE_DATE + "_sortable", reverse);
		}
		else if (orderByCol.equals("name")) {
			sort = SortFactoryUtil.create(
				Field.NAME, Sort.STRING_TYPE, reverse);
		}
		else if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(
				Field.PRIORITY, Sort.INT_TYPE, reverse);
		}

		return sort;
	}

	public static OrderByComparator<CPDefinitionOptionValueRel>
		getCPDefinitionOptionValueRelOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator =
				new CPDefinitionOptionValueRelPriorityComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CPDefinitionOptionValueRelNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPDefinitionOptionValueRelSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(
				Field.PRIORITY, Sort.INT_TYPE, reverse);
		}
		else if (orderByCol.equals("name")) {
			sort = SortFactoryUtil.create(
				Field.NAME, Sort.STRING_TYPE, reverse);
		}

		return sort;
	}

	public static OrderByComparator<CPDefinition>
		getCPDefinitionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinition> orderByComparator = null;

		if (orderByCol.equals("display-date")) {
			orderByComparator = new CPDefinitionDisplayDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new CPDefinitionModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CPDefinitionNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPDefinitionSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create(
				CPDefinitionIndexer.FIELD_DISPLAY_DATE + "_Number_sortable",
				reverse);
		}
		else if (orderByCol.equals("modified-date")) {
			sort = SortFactoryUtil.create(
				Field.MODIFIED_DATE + "_sortable", reverse);
		}
		else if (orderByCol.equals("title")) {
			sort = SortFactoryUtil.create(
				Field.TITLE, Sort.STRING_TYPE, reverse);
		}

		return sort;
	}

	public static OrderByComparator<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValueOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator =
				new CPDefinitionSpecificationOptionValuePriorityComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CPInstance> getCPInstanceOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPInstance> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPInstanceCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CPInstanceDisplayDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("sku")) {
			orderByComparator = new CPInstanceSkuComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCPInstanceSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(
				Field.CREATE_DATE + "_sortable", reverse);
		}
		else if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create(
				CPInstanceIndexer.FIELD_DISPLAY_DATE + "_Number_sortable",
				reverse);
		}
		else if (orderByCol.equals("sku")) {
			sort = SortFactoryUtil.create(
				CPInstanceIndexer.FIELD_SKU, Sort.STRING_TYPE, reverse);
		}

		return sort;
	}

}