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

import ClayMultiSelect from '@clayui/multi-select';
import React, { useEffect, useState } from 'react';

const spritemap = Liferay.ThemeDisplay.getCDNBaseURL() + "/o/admin-theme/images/clay/icons.svg";

export default function () {
  const [items, setItems] = useState([]);
  const [value, setValue] = useState('');

  useEffect(() => {
    window.addEventListener('customer-portal-select-user-loading', ({ detail: {accountBriefs} }) => {
      setItems(accountBriefs.map(({ externalReferenceCode, name }) => ({
        label: name,
        value: externalReferenceCode
      })))
    });
  }, []);

  return (
    <ClayMultiSelect
      inputName="myInput"
      inputValue={value}
      items={items}
      onChange={setValue}
      onItemsChange={setItems}
      spritemap={spritemap}
    />
  );
}
