package com.liferay.portal.convert;


import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Ivan Zaera
 */
public interface DLStoreConvertProcess {

	public void migrate(DLStoreConverter DLStoreConverter)
		throws PortalException;

}
