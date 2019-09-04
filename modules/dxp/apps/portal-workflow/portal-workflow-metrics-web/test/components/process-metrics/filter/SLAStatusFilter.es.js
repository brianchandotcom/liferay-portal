import {cleanup, render} from '@testing-library/react';
import {MockRouter} from '../../../mock/MockRouter.es';
import React from 'react';
import SLAStatusFilter from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/SLAStatusFilter.es';
import {SLAStatusProvider} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/SLAStatusStore.es';

describe('The SLA status filter should', () => {
	afterEach(cleanup);

	test('Be rendered with "onTime", "overdue", and "pending" items', () => {
		const {getAllByTestId} = render(
			<MockSLAStatusContext>
				<SLAStatusFilter />
			</MockSLAStatusContext>
		);

		const filterItemNames = getAllByTestId('filterItemName');

		expect(filterItemNames[0].innerHTML).toBe(
			Liferay.Language.get('on-time')
		);
		expect(filterItemNames[1].innerHTML).toBe(
			Liferay.Language.get('overdue')
		);
		expect(filterItemNames[2].innerHTML).toBe(
			Liferay.Language.get('untracked')
		);
	});
});

const MockSLAStatusContext = ({children}) => (
	<MockRouter>
		<SLAStatusProvider slaStatusKeys={[]}>{children}</SLAStatusProvider>
	</MockRouter>
);
