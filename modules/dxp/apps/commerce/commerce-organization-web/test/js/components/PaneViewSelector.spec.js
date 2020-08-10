import React from 'react';
import {mount} from 'enzyme';
import PaneViewSelector from 'components/PaneViewSelector';

describe('PaneViewSelector', () => {
    it('renders correctly with list-by USERS selected by default', () => {
        const inputProps = {
                onViewSelected: () => {},
                totalUsers: 5,
                totalAccounts: 4,
                listBy: 'user'
            },
            wrapper = mount(<PaneViewSelector {...inputProps} />);

        expect(wrapper.find('span').get(0).props).toMatchObject({
            className: 'selected-pane',
            children: `${inputProps.listBy} (${inputProps.totalUsers})`,
            role: 'button',
            tabIndex: '-1'
        });
    });

    it('lists members by account if the related tab is selected', () => {
        const inputProps = {
                onViewSelected: () => {},
                totalUsers: 5,
                totalAccounts: 4,
                listBy: 'account'
            },
            wrapper = mount(<PaneViewSelector {...inputProps} />);

        expect(wrapper.find('span').get(1).props).toMatchObject({
            className: 'selected-pane',
            children: `account (${inputProps.totalAccounts})`,
            role: 'button',
            tabIndex: '-1'
        });
    });
});
