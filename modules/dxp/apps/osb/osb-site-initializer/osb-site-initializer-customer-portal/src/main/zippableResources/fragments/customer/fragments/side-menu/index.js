/* eslint-disable no-undef */
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

fragmentElement.addEventListener("click", (event) => {
  const items = fragmentElement.querySelectorAll(".menu-item-content");

  if (event.target.classList.contains("menu-item-content")) {
    event.target.classList.toggle("active");
  }

  items.forEach((f) => {
    if (f.classList.contains("active") != event.target) {
      if (f != event.target) {
        f.classList.remove("active");
      }
    }
  });
  if (event.target.id == "subMenu") {
    const arrow = fragmentElement.querySelector(".arrow");
    const menu = fragmentElement.querySelector(".sub-menu");
    arrow.classList.toggle("arrow-left");
    arrow.classList.toggle("arrow-down");
    menu.classList.toggle("sub-menu-closed");
    menu.classList.toggle("sub-menu-open");
  }
});