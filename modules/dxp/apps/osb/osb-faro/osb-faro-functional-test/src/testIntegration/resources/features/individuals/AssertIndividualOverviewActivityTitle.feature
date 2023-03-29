@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Clicking an activity navigates to page in Individuals overview
	As a Business User, I should be able to click an activity title

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Open activity title navigate to page
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I click "Visited www.elliot-feeney.co" in the table
		And I click "reinvent intuitive e-services" in the table
		Then I should see the Sites Page Overview Cards with their details