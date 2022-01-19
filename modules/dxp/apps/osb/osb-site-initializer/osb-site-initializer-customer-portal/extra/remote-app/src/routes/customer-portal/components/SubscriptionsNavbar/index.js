/* eslint-disable no-console */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import { DropDown } from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import {useCustomerPortal} from '../../context';

const SubscriptionsNavbar = ({
  selectedSubscriptionGroup,
  setSelectedSubscriptionGroup,
  subscriptionGroups
}) => {

  const [active, setActive] = useState(false);
  const [selectedButton, setSelectedButton] = useState(subscriptionGroups[0]?.name);
  const [showDropDown, setShowDropDown] = useState(false);
  const [{isQuickLinksExpanded}] = useCustomerPortal();

  const subscriptionNavbarRef = useRef();

  const handleClick = useCallback((event) => {
    setSelectedSubscriptionGroup(event.target.value);
    setSelectedButton(event.target.value);
  }, [setSelectedSubscriptionGroup])

  useEffect(() => {
    setSelectedSubscriptionGroup(subscriptionGroups[0]?.name);
    setSelectedButton(subscriptionGroups[0]?.name);
  }, [setSelectedSubscriptionGroup, subscriptionGroups]);

  useEffect(() => {
    const updateShowDropDown = () => {
      setShowDropDown(isQuickLinksExpanded ? subscriptionNavbarRef.current.offsetWidth < 500 : subscriptionNavbarRef.current.offsetWidth < 570);
    };

    updateShowDropDown();
    window.addEventListener("resize", updateShowDropDown);
  }, [isQuickLinksExpanded]);

 

  return (
    <div className="d-flex rounded-pill w-100" ref={subscriptionNavbarRef}>
      <nav className="mb-2 mt-4 pt-2">
        {subscriptionGroups.length === 1 && subscriptionGroups.map((tag) => (
          <span
            className="h5 text-brand-primary"
            key={tag.name}
          >
            {tag.name}
          </span>
        ))}

        {subscriptionGroups.length > 1 && subscriptionGroups.length <= 4 &&
          <>
            {showDropDown && 
              <div className="align-items-center d-flex mt-4 pb-3 subscription-navbar-dropdown">
                <h6>Type:</h6>

                <DropDown
                  active={active}
                  closeOnClickOutside
                  menuElementAttrs={{
                    className: 'subscription-group-filter',
                  }}
                  onActiveChange={setActive}
                  trigger={
                    <ClayButton
                      className="font-weight-semi-bold ml-2 pb-2 shadow-none text-brand-primary"
                      displayType="unstyled"
                    >
                      {selectedSubscriptionGroup}
                      <></>
                      <ClayIcon symbol="caret-bottom" />
                    </ClayButton>
                  }
                >
                  {subscriptionGroups.map((tag) => (
                    <DropDown.Item
                      key={tag.name}
                      onClick={(event) => setSelectedSubscriptionGroup(event.target.value)}
                      value={tag.name}
                    >
                      {tag.name}
                    </DropDown.Item>
                  ))}
                </DropDown>
              </div>
            } 

            {!showDropDown && 
              <div
                className={classNames('bg-neutral-1 btn-group rounded-pill subscription-navbar')}
                id="subscription-navbar"
                role="group"
              >
                {subscriptionGroups.map((tag) => (
                  <button
                    className={selectedButton === tag.name
                      ?
                      "btn btn-subscription-group-selected label-primary px-4 rounded-pill text-neutral-4"
                      :
                      "btn btn-subscription-group px-4 rounded-pill text-neutral-4"}
                    key={tag.name}
                    onClick={handleClick}
                    value={tag.name}
                  >
                    {tag.name}
                  </button>
                ))}
              </div>
            }            
          </>
        }

        {subscriptionGroups.length > 4 &&
          <div className="align-items-center d-flex mr-4 mt-4 pb-3">
            <h6>Type:</h6>

            <DropDown
              active={active}
              closeOnClickOutside
              menuElementAttrs={{
                className: 'subscription-group-filter',
              }}
              onActiveChange={setActive}
              trigger={
                <ClayButton
                  className="font-weight-semi-bold ml-2 pb-2 shadow-none text-brand-primary"
                  displayType="unstyled"
                >
                  {selectedSubscriptionGroup}
                  <></>
                  <ClayIcon symbol="caret-bottom" />
                </ClayButton>
              }
            >
              {subscriptionGroups.map((tag) => (
                <DropDown.Item
                  key={tag.name}
                  onClick={(event) => setSelectedSubscriptionGroup(event.target.value)}
                  value={tag.name}
                >
                  {tag.name}
                </DropDown.Item>
              ))}
            </DropDown>
          </div>
        }
      </nav>
    </div>
  );
};

export default SubscriptionsNavbar;
