package com.liferay.portal.convert;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Ivan Zaera
 */
public interface DLStoreConverter {

	public void migrateDLFileEntry(
		long companyId, long repositoryId, DLFileEntry dlFileEntry);

}
