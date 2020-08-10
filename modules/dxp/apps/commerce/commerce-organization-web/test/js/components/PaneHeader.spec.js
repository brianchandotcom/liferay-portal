import React from 'react';
import {shallow} from 'enzyme';
import PaneHeader from 'components/PaneHeader';

describe('PaneHeader', () => {
    it('renders the pane header with the PaneOrgInfo, PaneViewSelector and PaneSearchBar components', () => {
        const inputProps = {
            orgName: 'Org name',
            totalSubOrg: 1,
            colorIdentifier: 'hsl(0,100%,100%)',
            onLookUp: expect.any(Function),
            onViewSelected: expect.any(Function),
            totalAccounts: 1,
            totalUsers: 1,
            spritemap: expect.any(String),
            listBy: 'user'
        };

        const wrapper = shallow(<PaneHeader {...inputProps} />);

        expect(wrapper.children('PaneOrgInfo').props()).toMatchObject({
            orgName: inputProps.orgName,
            childrenNo: inputProps.totalSubOrg,
            colorIdentifier: inputProps.colorIdentifier,
            showMenu: expect.any(Function)
        });
        expect(wrapper.children('PaneViewSelector').props()).toMatchObject({
            listBy: inputProps.listBy,
            totalAccounts: inputProps.totalAccounts,
            totalUsers: inputProps.totalUsers,
            onViewSelected: inputProps.onViewSelected
        });
        expect(wrapper.children('PaneSearchBar').props()).toMatchObject({
            onLookUp: inputProps.onLookUp,
            spritemap: inputProps.spritemap
        });
    });
});
