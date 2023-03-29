@spira_Data_Source @Data_Source @DXP @Token @Property @team_FARO @priority_3 @prototype
Feature: Assign sites to property and create a combined property
	As a Business User, I should be able to create a combined property with multiple sites

	Background: [Setup] Add additional site and connect DXP
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I click the "Liferay DXP" button
		* I click the "Connect with Token" button
		* I copy the DXP Authentication Token
		* I set up the local DXP instance
		* I connect Analytics Cloud to DXP using the Authentication Token
		* I create a new Site named "Combine site" in DXP

	Scenario: Combine sites into property
		Given I go to the "Synced Sites" DXP Page
		When I create a new Combined Property with the following Sites on DXP:
			| Liferay DXP | Combine site |
		And I go to the "Home" page
		And I login as "test@faro.io:test"
		And I go to the "Properties" page
		Then I should only see a Property named "Liferay DXP Combined Property" in the table