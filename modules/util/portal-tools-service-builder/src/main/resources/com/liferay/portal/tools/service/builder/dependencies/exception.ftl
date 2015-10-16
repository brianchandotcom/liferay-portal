<#include "copyright.txt" parse="false">


<#if osgiModule>package ${packagePath}.exception;<#else>package ${packagePath};</#if>

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author ${author}
 */
@ProviderType
public class ${exception}Exception extends PortalException {

	public ${exception}Exception() {
	}

	public ${exception}Exception(String msg) {
		super(msg);
	}

	public ${exception}Exception(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ${exception}Exception(Throwable cause) {
		super(cause);
	}

}