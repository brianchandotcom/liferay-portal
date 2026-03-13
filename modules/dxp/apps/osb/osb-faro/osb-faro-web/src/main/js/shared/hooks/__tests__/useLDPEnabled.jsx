import mockStore from 'test/mock-store';
import React from 'react';
import {fromJS} from 'immutable';
import {Provider} from 'react-redux';
import {renderHook} from '@testing-library/react-hooks';
import {useLDPEnabled} from 'shared/hooks/useLDPEnabled';

describe('useLDPEnabled', () => {
	it('should return true when the plan name contains "Data Platform"', () => {
		const groupId = '23';

		const initialState = fromJS({
			projects: {
				[groupId]: {
					data: {
						faroSubscription: {
							name: 'Liferay Data Platform'
						}
					}
				}
			}
		});

		const store = mockStore(initialState);
		const wrapper = ({children}) => (
			<Provider store={store}>{children}</Provider>
		);

		const {result} = renderHook(() => useLDPEnabled({groupId}), {wrapper});

		expect(result.current).toBe(true);
	});

	it('should return false when the plan name does NOT contain "Data Platform"', () => {
		const groupId = '123';
		const initialState = fromJS({
			projects: {
				[groupId]: {
					data: {
						faroSubscription: {
							name: 'Liferay Analytics Cloud Enterprise'
						}
					}
				}
			}
		});

		const store = mockStore(initialState);
		const wrapper = ({children}) => (
			<Provider store={store}>{children}</Provider>
		);

		const {result} = renderHook(() => useLDPEnabled({groupId}), {wrapper});

		expect(result.current).toBe(false);
	});
});
