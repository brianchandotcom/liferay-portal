import EditEmailReportsModal from '../EditEmailReportsModal';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {close} from 'shared/actions/modals';
import {fireEvent, render} from '@testing-library/react';
import {Frequency} from 'settings/channels/components/EmailReports';

jest.mock('shared/actions/alerts', () => ({
	actionTypes: {},
	addAlert: jest.fn(() => ({
		meta: {},
		payload: {},
		type: 'addAlert'
	}))
}));

addAlert({
	alertType: Alert.Types.Success,
	message: Liferay.Language.get('changes-to-email-reports-saved')
});

jest.unmock('react-dom');

describe('EditEmailReportsModal', () => {
	it('should render', () => {
		const {container} = render(
			<EditEmailReportsModal
				onCancel={close}
				onSave={null}
				report={{enabled: false, frequency: Frequency.Monthly}}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should click the toggle switch, check if its value has changed to true and if frequency options are enabled', () => {
		const {container} = render(
			<EditEmailReportsModal
				onCancel={close}
				onSave={null}
				report={{enabled: false, frequency: Frequency.Monthly}}
			/>
		);

		const frequency = document.getElementById('frequency');

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		expect(container).toContainElement(toggleSwitch);

		expect(container).toContainElement(frequency);

		expect(toggleSwitch).toHaveAttribute('value', 'false');

		expect(frequency).toHaveAttribute('disabled');

		fireEvent.click(toggleSwitch);

		expect(toggleSwitch).toHaveAttribute('value', 'true');

		expect(frequency).toBeEnabled();

		expect(frequency).not.toHaveAttribute('disabled');
	});

	it('should configure email reports with the MONTHLY option', () => {
		render(
			<EditEmailReportsModal
				onCancel={close}
				onSave={null}
				report={{enabled: true, frequency: Frequency.Monthly}}
			/>
		);

		const frequency = document.getElementById('frequency');

		const monthly = document.querySelector('option[value="monthly"]');

		const saveBtn = document.querySelector(
			'.modal-footer button[type="submit"]'
		);

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		fireEvent.click(toggleSwitch);

		fireEvent.click(frequency);

		fireEvent.click(monthly);

		fireEvent.click(saveBtn);

		jest.runAllTimers();

		expect(addAlert).toBeCalled();
	});

	it('should configure email reports with the WEEKLY option', () => {
		render(
			<EditEmailReportsModal
				onCancel={close}
				onSave={null}
				report={{enabled: true, frequency: Frequency.Weekly}}
			/>
		);

		const frequency = document.getElementById('frequency');

		const saveBtn = document.querySelector(
			'.modal-footer button[type="submit"]'
		);

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		const weekly = document.querySelector('option[value="weekly"]');

		fireEvent.click(toggleSwitch);

		fireEvent.click(frequency);

		fireEvent.click(weekly);

		fireEvent.click(saveBtn);

		jest.runAllTimers();

		expect(addAlert).toBeCalled();
	});

	it('should configure email reports with the DAILY option', () => {
		render(
			<EditEmailReportsModal
				onCancel={close}
				onSave={null}
				report={{enabled: true, frequency: Frequency.Daily}}
			/>
		);

		const daily = document.querySelector('option[value="daily"]');

		const frequency = document.getElementById('frequency');

		const saveBtn = document.querySelector(
			'.modal-footer button[type="submit"]'
		);

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		fireEvent.click(toggleSwitch);

		fireEvent.click(frequency);

		fireEvent.click(daily);

		fireEvent.click(saveBtn);

		jest.runAllTimers();

		expect(addAlert).toBeCalled();
	});
});
