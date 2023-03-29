@component_Accounts @team_FARO @priority_3
Feature: Expand activity in account activities
	As a Business User, I should be able to expand activity in account activities

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Expand activity session
		Given I click "Schneider and Sons" in the table
		And I click the "Activities" tab
		When I click "Visited www.debra-huel.name" in the table
		Then I should see an expanded item named "Visited innovate dot-com methodologies"