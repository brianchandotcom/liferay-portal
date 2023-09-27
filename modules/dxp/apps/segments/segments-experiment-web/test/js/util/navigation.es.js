/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getSegmentsExperimentAction} from '../../../src/main/resources/META-INF/resources/js/util/navigation.es';

describe('getSegmentsExperimentAction', () => {
	it('remove segmentsExperimentAction from URL and return it', () => {
		const initialUrl =
			'https://liferay.com/web/guest/experiment?segmentsExperimentKey=636029195608829519&segmentsExperimentAction=reviewAndRun';

		delete window.location;
		window.location = new URL(initialUrl);

		delete window.history.replaceState;
		window.history.replaceState = jest.fn();

		const result = getSegmentsExperimentAction();

		expect(window.history.replaceState).toHaveBeenCalledWith(
			null,
			null,
			'https://liferay.com/web/guest/experiment?segmentsExperimentKey=636029195608829519'
		);
		expect(result).toEqual('reviewAndRun');
	});
});
