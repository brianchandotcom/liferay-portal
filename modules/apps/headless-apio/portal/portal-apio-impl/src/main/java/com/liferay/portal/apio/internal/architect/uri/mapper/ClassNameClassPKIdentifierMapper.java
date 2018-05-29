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

package com.liferay.portal.apio.internal.architect.uri.mapper;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.uri.mapper.PathIdentifierMapper;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Converts a {@link Path} to a {@link ClassNameClassPK}, and vice versa.
 *
 * <p>
 * The {@code ClassNameClassPK} can then be used as the identifier of a
 * resource.
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ClassNameClassPKIdentifierMapper
	implements PathIdentifierMapper<ClassNameClassPK> {

	@Override
	public ClassNameClassPK map(Path path) {
		String id = path.getId();

		String[] components = id.split(":");

		if (components.length != 2) {
			throw new BadRequestException(
				id + " should be a string with the form " +
					"\"classNameId:classPK\"");
		}

		long classNameId = _getAsLong(components[0]);
		long classPK = _getAsLong(components[1]);

		String className = Optional.ofNullable(
			_classNameLocalService.fetchByClassNameId(classNameId)
		).map(
			ClassName::getClassName
		).orElseThrow(
			() -> new BadRequestException(
				"Unable to convert " + classNameId + " to a class name")
		);

		return ClassNameClassPK.create(className, classPK);
	}

	@Override
	public Path map(String name, ClassNameClassPK classNameClassPK) {
		String className = classNameClassPK.getClassName();

		long classNameId = _classNameLocalService.getClassNameId(className);

		String id = classNameId + ":" + classNameClassPK.getClassPK();

		return new Path(name, id);
	}

	private long _getAsLong(String string) {
		return Try.fromFallible(
			() -> GetterUtil.getLong(string)
		).orElseThrow(
			() -> new BadRequestException(
				"Unable to convert " + string + " to a long")
		);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

}