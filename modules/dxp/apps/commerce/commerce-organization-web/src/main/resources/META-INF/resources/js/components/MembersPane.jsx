import React, {Component} from 'react';
import PropTypes from 'prop-types';

import PaneHeader from './PaneHeader';
import MembersList from './MembersList';
import {LIST_BY} from '../utils/constants.es';
import {callApi, bindAll} from '../utils/utils.es';

const {USERS} = LIST_BY;

let membersListCopy = [];

function fetchMembers(apiURL, orgId, listBy, q = '') {
    const collectionPath = listBy + 's',
        apiParams = {
            baseURL: apiURL,
            id: orgId
        },
        apiParamsMembers = Object.assign({},
            apiParams,
            {
                path: collectionPath,
                queryParams: {
                    page: 1,
                    pageSize: 100,
                    q
                }
            });

    return Promise.all([
        callApi(apiParams),
        callApi(apiParamsMembers)
    ])
        .then(data => {
            const [
                orgData,
                membersData
            ] = data;

            const {
                name: orgName,
                organizationsTotal: totalSubOrg,
                accountsTotal,
                usersTotal
            } = orgData;

            return {
                id: orgId,
                orgName,
                totalSubOrg,
                totalUsers: usersTotal,
                totalAccounts: accountsTotal,
                members: membersData[collectionPath]
            };
        })
}

function filterMembers(name, members) {
    return members.filter(
        member => member.name
            .toLowerCase()
            .includes(name.toLowerCase())
    );
}

class MembersPane extends Component {
    constructor(props) {
        super(props);

        this.state = {
            searchQuery: '',
            listBy: USERS,
            isLoading: true
        };

        bindAll(
            this,
            'handleListBy',
            'handleLookUp',
            'handleUpdate'
        );
    }

    componentDidMount() {
        const {id} = this.props;
        const {listBy} = this.state;

        this.$didUpdate = this.handleUpdate(id, listBy);
    }

    componentDidUpdate(prevProps, prevState) {
        const {id} = this.props;
        const {listBy} = this.state;

        if (id !== prevProps.id || listBy !== prevState.listBy) {
            this.setState(() => ({isLoading: true}), () => {
                this.$didUpdate = this.handleUpdate(id, listBy)
            });
        }
    }

    handleListBy(listBy) {
        this.setState(() => ({
            listBy
        }));
    }

    handleLookUp(e) {
        const name = e.target.value,
            {id, apiURL} = this.props,
            {listBy} = this.state,
            fromState = !!name && name.length ?
                filterMembers(name, this.state.members) : membersListCopy;

        if (fromState.length) {
            return new Promise(resolve => {
                this.setState(() => ({
                    members: fromState
                }), () => {
                    resolve(true);
                })
            });
        } else {
            return fetchMembers(apiURL, id, listBy, name)
                .then(({total, users}) => {
                    this.setState(() => {
                        if (!!total) {
                            const fromFetch = filterMembers(name, users);

                            return fromFetch.length ?
                                {members: fromFetch} :
                                {members: membersListCopy}
                        }

                        return {members: membersListCopy};
                    });
                })
        }
    }

    handleUpdate(id, listBy) {
        const {apiURL} = this.props;

        return fetchMembers(apiURL, id, listBy)
            .then((data) => {
                this.setState(state => Object.assign(
                    state,
                    data,
                    {
                        isLoading: false
                    }), () => {
                    membersListCopy = this.state.members;
                });
            })
    }

    render() {
        const {
                orgName,
                members,
                totalSubOrg,
                totalUsers,
                totalAccounts,
                listBy
            } = this.state,
            paneClasses = 'pane pane-open';

        return (
            <div className={paneClasses}>
                <PaneHeader
                    orgName={orgName}
                    totalSubOrg={totalSubOrg}
                    totalUsers={totalUsers}
                    totalAccounts={totalAccounts}
                    listBy={this.state.listBy}
                    onViewSelected={this.handleListBy}
                    onLookUp={this.handleLookUp}
                    spritemap={this.props.spritemap}
                    colorIdentifier={this.props.colorIdentifier}
                />

                <MembersList
                    listBy={listBy}
                    members={members}
                    isLoading={this.state.isLoading}
                    spritemap={this.props.spritemap}
                    imagesPath={this.props.imagesPath}
                />
            </div>
        );
    }
}

PropTypes.defaultProps = {
    selectedId: 0,
    apiURL: ''
};

MembersPane.propTypes = {
    apiURL: PropTypes.string,
    selectedId: PropTypes.number
};

export default MembersPane;
