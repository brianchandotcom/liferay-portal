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

/* eslint-disable no-undef */

import Soy from 'metal-soy';

/**
 * Create a fake template to use Soy.register so that ComponentAdapter
 * is called instead.
 */
const FakeSoyTemplate = (ComponentAdapter, variant) => {
	let templates;
	goog.loadModule((exports) => {
		goog.require('soy');
		goog.module(variant + 'Adapter.incrementaldom');
		goog.require('soydata');
		goog.require('soy.idom');

		exports.render = () => {};
		exports.render.params = [];
		exports.render.types = {};
		exports.templates = templates = exports;

		return exports;
	});
	Soy.register(ComponentAdapter, templates);

	return templates;
};

/**
 * Basically it is an implementation of a module with a deltemplate in
 * compiled soy, this registers the field so that it can be called dynamically.
 * Anyone using ReactComponentAdapter does not need to know this information,
 * it will be removed when we have an implementation in React.
 */
export const SoyRegisterAdapter = (ComponentAdapter, variant) => {
	FakeSoyTemplate(ComponentAdapter, variant);
	goog.loadModule((exports) => {
		const soy = goog.require('soy');

		goog.module(variant + 'Register.incrementaldom');
		goog.require('soydata');
		goog.require('soy.idom');

		// We call the template created above so that Metal
		// can take care of the rest.

		const templateAlias = Soy.getTemplate(
			variant + 'Adapter.incrementaldom',
			'render'
		);

		const deltemplate_variant = (
			opt_data,
			opt_ijData,
			opt_ijData_deprecated
		) => {
			opt_ijData = opt_ijData_deprecated || opt_ijData;
			opt_data = opt_data || {};
			templateAlias(opt_data, null, opt_ijData);
		};

		exports.deltemplate_variant = deltemplate_variant;

		soy.$$registerDelegateFn(
			soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'),
			variant,
			0,
			deltemplate_variant
		);
		exports.templates = exports;

		return exports;
	});
};
