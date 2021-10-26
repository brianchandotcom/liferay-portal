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

const accountBriefs = {
  data: {
    userAccount: {
      accountBriefs: [
        {
          externalReferenceCode: "KOR-1906377",
          id: 45491,
          name: "Autostrade per L'Italia - New Public Webs",
        },
        {
          externalReferenceCode: "com.vistaprint.Cardguard",
          id: 1,
          name: "Enterprise Financial Services Corporation",
        },
        {
          externalReferenceCode: "com.mozilla.Quo Lux",
          id: 2,
          name: "Pebblebrook Hotel Trust",
        },
        {
          externalReferenceCode: "au.net.abc.Subin",
          id: 3,
          name: "Arista Networks, Inc.",
        },
        {
          externalReferenceCode: "com.lulu.Solarbreeze",
          id: 4,
          name: "Nomura Holdings Inc ADR",
        },
        {
          externalReferenceCode: "net.comcast.Sonair",
          id: 5,
          name: "Wins Finance Holdings Inc.",
        },
        {
          externalReferenceCode: "com.ycombinator.Biodex",
          id: 6,
          name: "Children's Place, Inc. (The)",
        },
        {
          externalReferenceCode: "com.photobucket.Viva",
          id: 7,
          name: "Abeona Therapeutics Inc.",
        },
        {
          externalReferenceCode: "com.omniture.Gembucket",
          id: 8,
          name: "Discover Financial Services",
        },
        {
          externalReferenceCode: "edu.yale.Konklux",
          id: 9,
          name: "Wells Fargo Global Dividend Opportunity Fund",
        },
        {
          externalReferenceCode: "com.bluehost.Tin",
          id: 10,
          name: "Net 1 UEPS Technologies, Inc.",
        },
      ],
      id: 45495,
      name: "Paolo Mazzinghi",
    },
  },
};

localStorage.setItem("accountBriefs", JSON.stringify(accountBriefs));
localStorage.setItem("projectSelected", JSON.stringify(accountBriefs.data.userAccount.accountBriefs[0].name));

const dropDownBtn = fragmentElement.querySelector('.dropdown-btn');
const arrow = fragmentElement.querySelector('.arrow-icon');
const card = fragmentElement.querySelector('.dropDownCard');
const list = fragmentElement.querySelector('#lista');
const sla = JSON.parse(localStorage.getItem('accountBriefs'));
const projectSelected = JSON.parse(localStorage.getItem('projectSelected'));
const projSelect = fragmentElement.querySelector('#selectedProject');

projSelect.innerHTML = projectSelected || "Lorem Ipsum";

const CardContent = () => {
  let html = ``;

  html = `<li class="active">
  <div class="d-flex">
    <div >${projectSelected}</div>
  </div>
  <span class="d-flex alig-items-center">
    <img src="./src/checked.svg" alt="" />
  </span>
</li>`;
  let count = 0;
  sla?.data?.userAccount?.accountBriefs?.map((element) => {
    count++;
    if (element.name !== projectSelected && count <= 6) {
      html += `
      <li>
      <p class="d-flex">
        ${element.name}
      </p>
    
    </li>
      `;
    }
  });
  html += `
  <li>
  <div class="d-flex">
    <div class="d-flex align-items-center">
      <img class="me-2" src="./src/arrow.svg" alt="" />
    </div>
    <div>All Projects</div>
  </div>
</li>
  `;

  list.innerHTML = html;
  card.style.left = `${arrow.offsetLeft - 35}px`;
  card.style.top = `${arrow.offsetTop - 10}px`;
};

CardContent();
function dropDown() {
  ['show', 'hiden'].map((prop) => {
    card.classList.toggle(prop);
  });

  ['left', 'down'].map((prop) => {
    arrow.classList.toggle(prop);
  });
}

dropDownBtn.addEventListener('click', dropDown);