import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import SegmentsExperimentsClickGoal from '../../../src/main/resources/META-INF/resources/js/components/ClickGoalPicker/ClickGoalPicker.es';
import React from 'react';
import {segmentsExperiment} from '../fixtures.es';

describe('Segments Experiments with Click Goal', () => {
	afterEach(cleanup);

	it('Experiment renders when goal value is click', () => {
		const experiment = {
			...segmentsExperiment,
			goal: {
				value: 'click'
			}
		};

		const {asFragment} = render(
			<SegmentsExperimentsClickGoal segmentsExperiment={experiment} />
		);

		expect(asFragment().children.length).not.toBe(0);
	});

	test.todo('Actually make the one above a meaningful test');

	test.todo(
		'Set click target section appears when draft experiment has goal set to click'
	);

	test.todo(
		'All clickable elements highlighted when the user clicks on set element button'
	);

	test.todo(
		'A tooltip click element to set as click target for your goal appears when the user clicks on set element button'
	);

	test.todo('Selectable as target elements show tooltips on hover');

	test.todo(
		'When the user set element as click target, then the element is set as the click target and the id of the element as a link'
	);

	test.todo(
		'Cancel selection click target element proccess when clicking out of selection zone'
	);

	test.todo(
		'When hovering over to invalid click target elements, the mouse is displayed in not-allowed mode'
	);

	test.todo(
		'The user can edit a selected click target in a draft experiment'
	);

	test.todo('The user can remove a selected click target in a draft element');

	test.todo(
		'The user clicks in the UI reference to the selected click target element, the page scrolls to make it visible'
	);
});
