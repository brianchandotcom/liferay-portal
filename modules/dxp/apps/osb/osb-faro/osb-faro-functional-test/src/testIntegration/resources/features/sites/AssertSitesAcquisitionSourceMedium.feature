@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Acquisition card Source|Medium highest session
	As a Business User, I should be able to view Source|Medium highest session in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert Acquisition Source|Medium highest session
		Given I go to the "Sites" page
		When I click the "Source | Medium" button
		Then I should see the Source/Medium named "www.dino-boyer.com / referral" in the table