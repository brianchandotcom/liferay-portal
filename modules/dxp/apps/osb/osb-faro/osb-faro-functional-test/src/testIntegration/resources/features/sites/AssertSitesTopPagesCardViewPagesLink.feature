@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Top Pages View Pages link navigates to pages tab
	As a Business User, I should be able to assert view pages link navigates to pages tab

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert View Pages link navigates to Pages tab
		Given I go to the "Sites" page
		When I click the "View Pages" button
		Then I search for "mesh synergistic schemas"
		And I should see pages named "mesh synergistic schemas" in the table