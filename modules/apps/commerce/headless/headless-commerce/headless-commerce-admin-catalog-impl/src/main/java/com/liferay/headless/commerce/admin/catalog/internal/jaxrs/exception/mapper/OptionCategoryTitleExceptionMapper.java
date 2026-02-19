package com.liferay.headless.commerce.admin.catalog.internal.jaxrs.exception.mapper;

import com.liferay.commerce.product.exception.CPOptionCategoryTitleException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Catalog)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Catalog.OptionCategoryTitleExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class OptionCategoryTitleExceptionMapper
	extends BaseExceptionMapper<CPOptionCategoryTitleException> {

	@Override
	protected Problem getProblem(
		CPOptionCategoryTitleException cpOptionCategoryTitleException) {

		return new Problem(cpOptionCategoryTitleException);
	}

}