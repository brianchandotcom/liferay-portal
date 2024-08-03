package com.liferay.headless.admin.user.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.OrganizationNameException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;
import org.osgi.service.component.annotations.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Lianne Louie
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.User)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.User.OrganizationNameMustNotBeNullExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class OrganizationNameMustNotBeNullExceptionMapper extends
	BaseExceptionMapper<OrganizationNameException.MustNotBeNull> {

	@Override
	protected Problem getProblem(
		OrganizationNameException.MustNotBeNull mustNotBeNull) {

		return new Problem(
			Response.Status.BAD_REQUEST,
			"A name is required to create an organization");
	}
}
