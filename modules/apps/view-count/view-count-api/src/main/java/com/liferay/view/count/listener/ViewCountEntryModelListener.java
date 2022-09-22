package com.liferay.view.count.listener;

import com.liferay.view.count.model.ViewCountEntry;

public interface ViewCountEntryModelListener {

	public void afterIncrement(ViewCountEntry viewCountEntry);

	public String getModelClassName();
}
