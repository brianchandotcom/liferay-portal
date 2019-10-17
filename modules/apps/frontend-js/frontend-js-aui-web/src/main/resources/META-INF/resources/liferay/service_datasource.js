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

AUI.add(
	'liferay-service-datasource',
	A => {
		var ServiceDataSource = A.Component.create({
			EXTENDS: A.DataSource.Local,
			NAME: 'servicedatasource',
			prototype: {
				_defRequestFn(event) {
					var instance = this;

					var source = instance.get('source');

					source(
						event.request,
						A.rbind('_serviceCallbackFn', instance, event)
					);
				},

				_serviceCallbackFn(obj, xHR, event) {
					var instance = this;

					instance.fire(
						'data',
						A.mix(
							{
								data: obj
							},
							event
						)
					);
				}
			}
		});

		Liferay.Service.DataSource = ServiceDataSource;
	},
	'',
	{
		requires: ['aui-base', 'datasource-local']
	}
);
