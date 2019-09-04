import {cleanup, render} from '@testing-library/react';
import {MockRouter} from '../../../mock/MockRouter.es';
import ProcessStatusFilter from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/ProcessStatusFilter.es';
import {ProcessStatusProvider} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/ProcessStatusStore.es';
import React from 'react';

describe('The process status filter should', () => {
	afterEach(cleanup);

	test('Be rendered with "completed" and "pending" items', () => {
		const {getAllByTestId} = render(
			<MockProcessStatusContext>
				<ProcessStatusFilter />
			</MockProcessStatusContext>
		);

		const filterItemNames = getAllByTestId('filterItemName');

		expect(filterItemNames[0].innerHTML).toBe(
			Liferay.Language.get('completed')
		);
		expect(filterItemNames[1].innerHTML).toBe(
			Liferay.Language.get('pending')
		);
	});
});

const MockProcessStatusContext = ({children}) => (
	<MockRouter>
		<ProcessStatusProvider processStatusKeys={[]}>
			{children}
		</ProcessStatusProvider>
	</MockRouter>
);
