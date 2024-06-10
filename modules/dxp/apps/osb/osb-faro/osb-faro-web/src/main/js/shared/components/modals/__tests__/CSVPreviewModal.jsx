import CSVPreviewModal from '../CSVPreviewModal';
import React from 'react';
import {noop} from 'lodash';
import {render, waitFor} from '@testing-library/react';

jest.unmock('react-dom');

describe('CSVPreviewModal', () => {
	it('should render', async () => {
		const {container} = render(
			<CSVPreviewModal
				fileName='test'
				groupId='23'
				id='test'
				onClose={noop}
			/>
		);

		await waitFor(() => {});

		expect(container).toMatchSnapshot();
	});

	it('should render with a title on the heading', () => {
		const {getByText} = render(
			<CSVPreviewModal
				fileName='test'
				groupId='23'
				id='test'
				name='Liferay Test'
				onClose={noop}
			/>
		);

		expect(getByText('Data Preview "Liferay Test"')).toBeTruthy();
	});
});
