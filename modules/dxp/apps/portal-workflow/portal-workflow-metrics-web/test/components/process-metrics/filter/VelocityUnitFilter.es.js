import {cleanup, render} from '@testing-library/react';
import {MockRouter} from '../../../mock/MockRouter.es';
import React from 'react';
import {TimeRangeContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import {VelocityUnitFilter} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/VelocityUnitFilter.es';
import {VelocityUnitProvider} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/VelocityUnitStore.es';

const timeRange = {
	dateEnd: new Date(2018, 3, 1),
	dateStart: new Date(2018, 1, 1)
};

describe('The velocity unit filter should', () => {
	afterEach(cleanup);

	test('Be rendered with "inst-day", "inst-week", and "inst-month" items', () => {
		const {getAllByTestId} = render(
			<MockVelocityUnitContext
				dateEnd={new Date(2018, 3, 1)}
				dateStart={new Date(2018, 1, 1)}
			>
				<VelocityUnitFilter />
			</MockVelocityUnitContext>
		);

		const filterItemNames = getAllByTestId('filterItemName');

		expect(filterItemNames[0].innerHTML).toBe(
			Liferay.Language.get('inst-day')
		);
		expect(filterItemNames[1].innerHTML).toBe(
			Liferay.Language.get('inst-week')
		);
		expect(filterItemNames[2].innerHTML).toBe(
			Liferay.Language.get('inst-month')
		);
	});
});

const MockVelocityUnitContext = ({children}) => (
	<MockRouter>
		<TimeRangeContext.Provider
			value={{
				getSelectedTimeRange: () => timeRange
			}}
		>
			<VelocityUnitProvider velocityUnitKeys={[]}>
				{children}
			</VelocityUnitProvider>
		</TimeRangeContext.Provider>
	</MockRouter>
);
