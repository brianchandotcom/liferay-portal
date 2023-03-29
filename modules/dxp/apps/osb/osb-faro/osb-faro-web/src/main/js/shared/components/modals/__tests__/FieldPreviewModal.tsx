import FieldPreviewModal from '../FieldPreviewModal';
import Promise from 'metal-promise';
import React from 'react';
import {noop} from 'lodash';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<FieldPreviewModal
		dataSourceFn={() => Promise.resolve()}
		onClose={noop}
		sourceName='foo'
		{...props}
	/>
);

describe('FieldPreviewModal', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with fieldname', () => {
		const fieldName = 'bar';

		const {getByText} = render(<DefaultComponent fieldName={fieldName} />);

		expect(getByText(fieldName)).toBeTruthy();
	});
});
