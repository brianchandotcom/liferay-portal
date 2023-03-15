import Form from 'shared/components/form';
import React from 'react';
import TimeZonePicker from '../TimeZonePicker';
import {render} from '@testing-library/react';
import {TimeZone} from 'shared/util/records';

jest.unmock('react-dom');

describe('TimeZonePicker', () => {
	it('should render', () => {
		jest.useFakeTimers();

		const {container} = render(
			<Form
				initialValues={{
					timezone: ''
				}}
			>
				{({setFieldTouched, setFieldValue}) => (
					<Form.Form>
						<TimeZonePicker
							fieldName='timezone'
							initialTimeZone={new TimeZone()}
							setFieldTouched={setFieldTouched}
							setFieldValue={setFieldValue}
						/>
					</Form.Form>
				)}
			</Form>
		);

		jest.runAllTimers();
		expect(container).toMatchSnapshot();
	});
});
