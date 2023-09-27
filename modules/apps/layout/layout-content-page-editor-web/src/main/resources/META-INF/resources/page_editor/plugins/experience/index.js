/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Component} from '../../common/AppContext';
import ExperienceToolbarSection from './components/ExperienceToolbarSection';
import ExperienceReducer from './reducers/index';

function renderExperiencesSection() {
	const {Component} = this;

	return (
		<Component>
			<ExperienceToolbarSection />
		</Component>
	);
}

/**
 * Entry-point for "Experience" (toolbar drop-down) functionality.
 */
export default class Experience {
	constructor({app, toolbarPlugin}) {
		this.Actions = app.Actions;
		this.Component = Component(app);
		this.dispatch = app.dispatch;

		this.toolbarId = app.config.toolbarId;
		this.toolbarPluginId = toolbarPlugin.toolbarPluginId;

		if (
			app.store.availableSegmentsExperiences !== undefined &&
			app.store.availableSegmentsExperiences !== null &&
			app.config.singleSegmentsExperienceMode !== true
		) {
			this.activate = experiencesActivate.bind(this);
			this.renderToolbarSection = renderExperiencesSection.bind(this);
		}
	}

	deactivate() {
		this.dispatch(this.Actions.unloadReducer(Experience.name));
	}
}

function experiencesActivate() {
	this.dispatch(this.Actions.loadReducer(ExperienceReducer, Experience.name));
}
