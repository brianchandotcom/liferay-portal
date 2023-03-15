import IndividualAttributesModal from '../IndividualAttributesModal';
import React from 'react';
import {mockIndividualAttributes} from 'test/data';
import {noop} from 'lodash';
import {render} from '@testing-library/react';

const {dataSources, fieldName} = mockIndividualAttributes();

jest.unmock('react-dom');

describe('IndividualAttributesModal', () => {
	it('should render', () => {
		const {container} = render(
			<IndividualAttributesModal
				dataSources={dataSources}
				fieldName={fieldName}
				onClose={noop}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
