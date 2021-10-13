let accountBriefs = {
        data: {
          userAccount: {
            id: 45495,
            name: "Paolo Mazzinghi",
            accountBriefs: [
              {
                id: 45491,
                name: "Autostrade per L'Italia - New Public Webs",
                externalReferenceCode: "KOR-1906377",
              },
              {
                id: 1,
                name: "Enterprise Financial Services Corporation",
                externalReferenceCode: "com.vistaprint.Cardguard",
              },
              {
                id: 2,
                name: "Pebblebrook Hotel Trust",
                externalReferenceCode: "com.mozilla.Quo Lux",
              },
              {
                id: 3,
                name: "Arista Networks, Inc.",
                externalReferenceCode: "au.net.abc.Subin",
              },
              {
                id: 4,
                name: "Nomura Holdings Inc ADR",
                externalReferenceCode: "com.lulu.Solarbreeze",
              },
              {
                id: 5,
                name: "Wins Finance Holdings Inc.",
                externalReferenceCode: "net.comcast.Sonair",
              },
              {
                id: 6,
                name: "Children's Place, Inc. (The)",
                externalReferenceCode: "com.ycombinator.Biodex",
              },
              {
                id: 7,
                name: "Abeona Therapeutics Inc.",
                externalReferenceCode: "com.photobucket.Viva",
              },
              {
                id: 8,
                name: "Discover Financial Services",
                externalReferenceCode: "com.omniture.Gembucket",
              },
              {
                id: 9,
                name: "Wells Fargo Global Dividend Opportunity Fund",
                externalReferenceCode: "edu.yale.Konklux",
              },
              {
                id: 10,
                name: "Net 1 UEPS Technologies, Inc.",
                externalReferenceCode: "com.bluehost.Tin",
              },
            ],
          },
        },
      };
localStorage.setItem("accountBriefs", JSON.stringify(accountBriefs));
localStorage.setItem("projectSelected",JSON.stringify(accountBriefs.data.userAccount.accountBriefs[0].name));
let dropDownBtn = fragmentElement.querySelector('.dropdown-btn');
let arrow = fragmentElement.querySelector('.arrow-icon');
let card = fragmentElement.querySelector('.dropDownCard');
let list = fragmentElement.querySelector('#lista');
let sla = JSON.parse(localStorage.getItem('accountBriefs'));
let projectSelected = JSON.parse(localStorage.getItem('projectSelected'));
let projSelect = fragmentElement.querySelector('#selectedProject');


projectSelected == null
  ? (projSelect.innerHTML = "Lorem Ipsum")
  : (projSelect.innerHTML = projectSelected);


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