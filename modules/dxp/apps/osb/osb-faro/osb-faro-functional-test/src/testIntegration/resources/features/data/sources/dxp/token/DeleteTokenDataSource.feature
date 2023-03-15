@spira_Data_Source @Data_Source @DXP @Token @team_FARO @priority_4 @prototype
Feature: Delete a DXP Data Source connected via Token Auth
	As a Business User, I should be able to delete a DXP Data Source connected via Token Auth

	Background: [Setup] Add DXP Data Source via Token Auth
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
		
	Scenario: Delete a DXP Data Source connected via Token Auth
		Given I go to the "Data Source" page
		And I click "Liferay DXP" in the table
		When I click the "Delete Data Source" button
		And I delete the "Liferay DXP" Data Source
		And I go to the "Data Source" page
		And I search for "Liferay DXP"
		Then I should not see a "DXP" Data Source named "Liferay DXP"