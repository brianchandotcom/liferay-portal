import React, {Component} from 'react';
import Member from './Member';
import NoMembers from './NoMembers';

class MembersList extends Component {
    render() {
        const {
            members,
            imagesPath,
            spritemap,
            isLoading
        } = this.props;

        return (
            <div className='pane-members-list'>
                {<ul>
                    {!isLoading && !!members.length &&
                        members.map((member, index) => {
                            return (
                                <Member
                                    key={index}
                                    member={member}
                                    imagesPath={imagesPath}
                                />
                            );
                        })
                    }
                </ul>
                }

                {!isLoading && !members.length &&
                    <NoMembers spritemap={spritemap}/>
                }
            </div>
        );
    }
}

MembersList.defaultProps = {};

MembersList.propTypes = {};

export default MembersList;
