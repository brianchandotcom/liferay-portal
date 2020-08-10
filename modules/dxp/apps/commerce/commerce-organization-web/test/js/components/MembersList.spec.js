import React from 'react';
import {shallow} from 'enzyme';
import MembersList from 'components/MembersList';

describe('MembersList', () => {
    it('renders the members list if there are members', () => {
        const props = {
                members: [{}, {}, {}],
                isLoading: false
            }, wrapper = shallow(<MembersList {...props} />);

        expect(wrapper.find('div').some('.pane-members-list')).toBe(true);
        expect(wrapper.find('ul').children().length).toEqual(3);
    });

    it('renders NoMembers component if there are no members', () => {
        const props = {
            members: [],
            isLoading: false
        }, wrapper = shallow(<MembersList {...props} />);

        expect(wrapper.find('div').some('.pane-members-list')).toBe(true);
        expect(wrapper.children('Member').length).toEqual(0);
        expect(wrapper.children('NoMembers').length).toEqual(1);
    });
});
