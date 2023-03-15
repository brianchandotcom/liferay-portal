@spira_Data_Source @Data_Source @DXP @Token @Property @team_FARO @priority_3 @prototype
Feature: Assign sites to property is blocked if the site is already assigned to another property
	As a Business User, I should not be able to assign a site to a property when it is already assigned

	Background: [Setup] Assign site to a property
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
		* I create a new Site named "Assigned site" in DXP
		* I go to the "Synced Sites" DXP Page
		* I create a new Multiple Property with the following Sites on DXP:
			| Liferay DXP |
		* I go to the "Synced Sites" DXP Page

	Scenario: Assert site is blocked from being selected to property
		Given I should see Property named "Liferay DXP" in the table
		When I go to the "Synced Sites" DXP Page
		Then I should not be able to create property with site "Liferay DXP"