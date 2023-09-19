/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactPortal} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {SIZES, Size} from '../constants/sizes';
import Preview from './Preview';
import SizeSelector from './SizeSelector';

import '../../css/main.scss';
import {CustomSizeContextProvider} from '../contexts/CustomSizeContext';

interface IProps {
	portletNamespace: string;
}

export default function App({portletNamespace: namespace}: IProps) {
	const [activeSize, setActiveSize] = useState<Size>(SIZES.desktop);
	const [open, setOpen] = useState<boolean>(false);

	const previewRef = useRef<HTMLDivElement>(null);

	const simulationPanel = useMemo(
		() => document.getElementById(`${namespace}simulationPanelId`),
		[namespace]
	);

	useEffect(() => {
		const wrapper = document.getElementById('wrapper');

		const simulationToggle = document.getElementById(
			`${namespace}simulationToggleId`
		);

		// @ts-ignore

		const sidenavInstance = Liferay.SideNavigation.initialize(
			simulationToggle
		);

		sidenavInstance.on('closed.lexicon.sidenav', () => {
			setOpen(false);

			if (wrapper) {
				wrapper.removeAttribute('inert');
			}
		});

		sidenavInstance.on('open.lexicon.sidenav', () => {
			setOpen(true);

			if (wrapper) {
				wrapper.setAttribute('inert', '');
			}
		});

		if (sidenavInstance && sidenavInstance.visible()) {
			setOpen(true);

			if (wrapper) {
				wrapper.setAttribute('inert', '');
			}
		}

		return () => {

			// @ts-ignores

			Liferay.SideNavigation.destroy(simulationToggle);
		};
	}, [namespace]);

	if (!simulationPanel) {
		return null;
	}

	return (
		<CustomSizeContextProvider>
			<SizeSelector
				activeSize={activeSize}
				namespace={namespace}
				open={open}
				previewRef={previewRef}
				setActiveSize={setActiveSize}
			/>

			<ReactPortal container={simulationPanel}>
				<Preview
					activeSize={activeSize}
					open={open}
					previewRef={previewRef}
				/>
			</ReactPortal>
		</CustomSizeContextProvider>
	);
}

App.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
};
