import ClayButton from "@clayui/button";
import ClayLink from "@clayui/link";
import ClayNavigationBar from "@clayui/navigation-bar";
import React, { useState } from "react";
import DropDown from "@clayui/drop-down";

export default function LowerSide() {
  const [active, setActive] = useState("Item 1");

  return (
    <ClayNavigationBar className="nav-container" triggerLabel={active}>
      <ClayNavigationBar.Item active={active === "Item 1"}>
        <ClayLink
          href="#"
          onClick={(event) => {
            event.preventDefault();
            setActive("Item 1");
          }}
        >
          <DropDown trigger={<div id="navButton">LA DOUANE MAROCAIN</div>}>
            <DropDown.ItemList>
              <DropDown.Item>one</DropDown.Item>
              <DropDown.Item>two</DropDown.Item>
              <DropDown.Item>three</DropDown.Item>
            </DropDown.ItemList>
          </DropDown>
        </ClayLink>
      </ClayNavigationBar.Item>
      <ClayNavigationBar.Item active={active === "Item 2"}>
        <ClayButton onClick={() => setActive("Item 2")}>
          <DropDown trigger={<div id="navButton">PARTICULIERS</div>}>
            <DropDown.ItemList>
              <DropDown.Item>one</DropDown.Item>
              <DropDown.Item>two</DropDown.Item>
              <DropDown.Item>three</DropDown.Item>
            </DropDown.ItemList>
          </DropDown>
        </ClayButton>
      </ClayNavigationBar.Item>
      <ClayNavigationBar.Item active={active === "Item 3"}>
        <ClayLink
          href="#"
          onClick={(event) => {
            event.preventDefault();
            setActive("Item 3");
          }}
        >
          <DropDown
            trigger={<div id="navButton">ENTREPRISES & PROFESSIONNELS</div>}
          >
            <DropDown.ItemList>
              <DropDown.Item>one</DropDown.Item>
              <DropDown.Item>two</DropDown.Item>
              <DropDown.Item>three</DropDown.Item>
            </DropDown.ItemList>
          </DropDown>
        </ClayLink>
      </ClayNavigationBar.Item>
      <ClayNavigationBar.Item active={active === "Item 4"}>
        <ClayLink
          href="#"
          onClick={(event) => {
            event.preventDefault();
            setActive("Item 4");
          }}
        >
          <DropDown trigger={<div id="navButton">SERVICES EN LIGNE</div>}>
            <DropDown.ItemList>
              <DropDown.Item>one</DropDown.Item>
              <DropDown.Item>two</DropDown.Item>
              <DropDown.Item>three</DropDown.Item>
            </DropDown.ItemList>
          </DropDown>
        </ClayLink>
      </ClayNavigationBar.Item>
    </ClayNavigationBar>
  );
}
