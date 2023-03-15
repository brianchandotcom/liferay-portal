@component_Accounts @team_FARO @priority_3
Feature: Account interests shows member percentage
	As a Business User, I should be able to view member percentage in account interests

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: View interest member percentage
		Given I click "Hilll, Gottlieb and Dicki" in the table
		When I click the "Interests" tab
		Then I should see an active member percentage "36.36%"