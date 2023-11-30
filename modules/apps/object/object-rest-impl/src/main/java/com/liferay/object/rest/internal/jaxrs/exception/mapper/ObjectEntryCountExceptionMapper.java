package com.liferay.object.rest.internal.jaxrs.exception.mapper;

import com.liferay.object.exception.ObjectEntryCountException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;
import org.osgi.service.component.annotations.Component;

import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Thalles Montenegro
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Object.Admin.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Object.Admin.REST.ObjectEntryCountExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class ObjectEntryCountExceptionMapper extends
	BaseExceptionMapper<ObjectEntryCountException> {

	@Override
	protected Problem getProblem(ObjectEntryCountException objectEntryCountException) {
		return new Problem(objectEntryCountException);
	}
}
