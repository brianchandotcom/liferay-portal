@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Expand activity in individual overview
	As a Business User, I should be able to expand activity for individual overview

	Background: [Setup] Navigate to Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Expand activity session
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I click "Visited www.elliot-feeney.co" in the table
		Then I should see an expanded item named "Visited reinvent intuitive e-services"