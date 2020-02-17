package com.liferay.item.selector;

import java.util.List;

/**
 * @author Alicia García
 */
public interface PortletItemSelectorView<T extends ItemSelectorCriterion>
	extends ItemSelectorView<T> {

	public List<String> getPortletIds();

}